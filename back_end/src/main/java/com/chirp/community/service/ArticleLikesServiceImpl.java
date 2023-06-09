package com.chirp.community.service;

import com.chirp.community.configuration.JpaAuditingConfig;
import com.chirp.community.entity.Article;
import com.chirp.community.entity.ArticleLikes;
import com.chirp.community.entity.SiteUser;
import com.chirp.community.model.LikesDto;
import com.chirp.community.model.SiteUserDto;
import com.chirp.community.repository.ArticleLikesRepository;
import com.chirp.community.type.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleLikesServiceImpl implements ArticleLikesService {
    private final ArticleLikesRepository articleLikesRepository;

    private ArticleLikes getHollowShell(Long articleId, SiteUser user) {
        Article articleEntity = new Article();
        articleEntity.setId(articleId);

        ArticleLikes entity = new ArticleLikes();
        entity.setArticle(articleEntity);
        entity.setUser(user);
        entity.setArg(LikeType.NULL);
        return entity;
    }

    private ArticleLikes loadByArticle_IdAndUser_Id(Long articleId, SiteUser user) {
        return articleLikesRepository.findByArticle_IdAndUser_Id(articleId, user.getId())
                .orElseGet(() -> getHollowShell(articleId, user));
    }

    private LikesDto printLikes(Long articleId, Function<ArticleLikes, ArticleLikes> function) {
        LikeType likeType = JpaAuditingConfig.principal()
                .map(SiteUserDto::toEntity)

                .map(siteUser -> loadByArticle_IdAndUser_Id(articleId, siteUser))
                .map(function::apply)

                .map(ArticleLikes::getArg)
                .orElse(LikeType.NULL);

        long totalLikes = articleLikesRepository.sumByArticle_Id(articleId);
        return LikesDto.builder()
                .likeType(likeType)
                .numLikes(totalLikes)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public LikesDto readLikes(Long articleId) {
        return printLikes(articleId, Function.identity());
    }

    @Override
    public LikesDto toggleLikes(Long articleId) {
        return printLikes(articleId, entity -> {
            if(entity.getArg().equals(LikeType.LIKE)) {
                entity.setArg(LikeType.NULL);
            } else {
                entity.setArg(LikeType.LIKE);
            }
            return articleLikesRepository.save(entity);
        });
    }

    @Override
    public LikesDto toggleDisLikes(Long articleId) {
        return printLikes(articleId, entity -> {
            if(entity.getArg().equals(LikeType.DISLIKE)) {
                entity.setArg(LikeType.NULL);
            } else {
                entity.setArg(LikeType.DISLIKE);
            }
            return articleLikesRepository.save(entity);
        });
    }
}
