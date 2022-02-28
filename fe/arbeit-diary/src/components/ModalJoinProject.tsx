import React from "react";
import "../css/components/ModalJoinProject.css";

type ModalJoinProjectProps = {
  visible: boolean;
  projectId: string;
  onCancelJoinModal: () => void;
};

function ModalJoinProject({
  visible,
  projectId,
  onCancelJoinModal,
}: ModalJoinProjectProps) {
  if (!visible) return <></>;

  return (
    <div className="backJoinProjectModal" onClick={onCancelJoinModal}>
      <div
        className="JoinProjectModal"
        onClick={(event) => event.stopPropagation()}
      >
        <p>당신의 팀원에게 참여코드를 전송해 주세요</p>
        <br />
        <br />
        <p>{projectId}</p>
      </div>
    </div>
  );
}

export default ModalJoinProject;
