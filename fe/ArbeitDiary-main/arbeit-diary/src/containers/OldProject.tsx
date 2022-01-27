import React from "react";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import "../css/containers/OldProject.css";

function OldProject() {
  return (
    <>
      <Header />
      <div className="diary-container">
        <div className="diary-inner-box Make-diary-box">새 프로젝트</div>
        <Link to="project" className="diary-inner-box Select-diary-box">
          기존 프로젝트1
        </Link>
        <div className="diary-inner-box Select-diary-box">기존 프로젝트2</div>
      </div>
    </>
  );
}

export default OldProject;
