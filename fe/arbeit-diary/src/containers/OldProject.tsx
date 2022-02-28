import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { checkEffectiveToken, CheckTokenMoveHome } from "../api/CheckToken";
import { useHistory } from "react-router";
import Header from "../components/Header";
import "../css/containers/OldProject.css";
import { RootState } from "../module";
import { getCalendarApi } from "../api/UserApi";

type ProjecttitleProps = {
  projectId: string;
  projectName: string;
};

function Projecttitle({ projectId, projectName }: ProjecttitleProps) {
  const dispatch = useDispatch();
  const ongetCalendarApi = async () => {
    const token = localStorage.getItem("token");
    await getCalendarApi(token !== null ? token : "", { projectId }, dispatch);
  };
  return (
    <Link
      to={`project/?projectId=${projectId}`}
      className="diary-inner-box Select-diary-box"
      onClick={ongetCalendarApi}
    >
      {projectName}
    </Link>
  );
}

function OldProject() {
  CheckTokenMoveHome();
  const { projects } = useSelector((state: RootState) => state.Userinfo)[0];

  const dispatch = useDispatch();
  const history = useHistory();
  useEffect(() => {
    checkEffectiveToken(dispatch, history);
  }, []);

  return (
    <>
      <Header />
      <div className="diary-container">
        <Link to="/newproject" className="diary-inner-box Make-diary-box">
          새 일기 만들기
        </Link>
        {projects.map((project, index) => {
          return (
            <Projecttitle
              key={index}
              projectId={project.projectId}
              projectName={project.projectName}
            />
          );
        })}
      </div>
    </>
  );
}

export default OldProject;
