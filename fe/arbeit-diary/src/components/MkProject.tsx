import React from "react";
import { Link } from "react-router-dom";
import CheckToken from "../api/CheckToken";
import "../css/MkProject.css";

function MkProject() {
  return (
    <div className="MkProject">
      <div className="description">
        <div className="logo-title"></div>
        <p>그날의 알바 스케줄과 ,</p>
        <br />
        <p>그날의 기록을 써내려가는</p>
        <br />
        <br />
        <p>매장전용 스케쥴러</p>

        <div className="project">
          <Link
            to={CheckToken() ? "/newproject" : "/login"}
            className="indexbtn"
          >
            일기 만들기
          </Link>
          <Link
            to={CheckToken() ? "/oldproject" : "/login"}
            className="indexbtn"
          >
            내 알바일기
          </Link>
          <Link
            to={CheckToken() ? "/joinproject" : "/login"}
            className="indexbtn"
          >
            일기 참여
          </Link>
        </div>
      </div>
      <div className="icon"></div>
    </div>
  );
}

export default MkProject;
