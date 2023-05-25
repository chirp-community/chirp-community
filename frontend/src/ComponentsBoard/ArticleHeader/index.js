import './index.css';


function ArticleHeader(props) {

    return (
        <div className="ArticleHeader">

            <header class="mb-4">
                {/* 게시글 제목 */}
                <h1 class="fw-bolder mb-1">{props.article.title}</h1>
                {/* 작성일, 작성자 */}
                <div class="d-flex justify-content-start flex-direction: column-reverse">
                    <div class="text-muted fst-italic mb-2">작성일</div>
                </div>
                <div class="d-flex justify-content-start flex-direction: column-reverse text-muted fst-italic mb-2">
                    작성자 : {props.writer.nickname}
                </div>
                {/* 태그 */}
                {/* <a class="badge bg-secondary text-decoration-none link-light" href="#!">태그01</a> */}
                {/* <a class="badge bg-secondary text-decoration-none link-light" href="#!">태그</a> */}
            </header>


        </div>
    );
}

export default ArticleHeader;