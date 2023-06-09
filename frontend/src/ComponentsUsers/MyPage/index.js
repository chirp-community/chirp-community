import React from "react";
import './css.css';

import * as c from '../../ComponentsUtils';

import Info from "./Info";
import ArticleList from "../UserPageCom/ArticleList";
import CommentList from "../UserPageCom/CommentList";
import EmailVerification from "../EmailVerification";
import { useSelector } from "react-redux";
import { hasSomethingInString } from "../../utils";

export default function Login() {
  const token = useSelector(state => state.AuthReducer.token);

  return (hasSomethingInString(token) ? 
    <c.Sheet className="total-size container">
      <div className="row">
        <div className="col-auto">
          <EmailVerification />
        </div>
        <div className="col">
          
        </div>
      </div>
      <div className="row">
        <div className="col">
          <Info className="info-size"/>
        </div>
        <div className="col-0">
          
        </div>
      </div>
      <div className="row">
        <div className="col">
            <ArticleList className="list-size" userName="내" />
        </div>
        <div className="col">
            <CommentList className="list-size" userName="내" />
        </div>
      </div>
    </c.Sheet>

    : 

    <c.Sheet className="total-size container">
      <img className="m-3" src="401-Unauthorized-t.jpg" alt="401 unauthorized" />
    </c.Sheet>
  );
}