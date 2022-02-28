import React from "react";
import { useHistory } from "react-router";
import { CheckTokenMoveHome } from "../api/CheckToken";
import { api, PostApi } from "../api/UserApi";
import Header from "../components/Header";
import "../css/containers/NewProject.css";
function JoinProject() {
  CheckTokenMoveHome();
  const token = localStorage.getItem("token");
  const history = useHistory();

  const joinProject = async (e: any) => {
    e.preventDefault();

    const success = () => {
      alert("성공적으로 참여되었습니다");
      history.push("/oldproject");
    };
    await PostApi(`${api}/api/joinproject`, token !== null ? token : "", {
      projectId: e.target.code.value,
    }).then(() => {
      localStorage.getItem("response") === "true"
        ? success()
        : alert("이미 참여되었거나 존재하지 않는 코드입니다");
    });
  };

  return (
    <>
      <Header />
      <div className="NewProject">
        <form onSubmit={joinProject}>
          <div className="Stepbox">
            참여코드 하나면 충분합니다!
            <input
              type="text"
              placeholder="참여코드를 입력하세요"
              id="code"
              className="question"
            ></input>
          </div>
          <button className="makeProject">참여하기!</button>
        </form>
      </div>
    </>
  );
}

export default JoinProject;
