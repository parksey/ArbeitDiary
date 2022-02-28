import React from "react";
import Record from "../components/Record";
import Schedule from "../components/Schedule";
import "../css/containers/DayDetail.css";

type DaydetailProps = {
  visible: boolean;
  onCancel: () => void;
  selectedDay: string;
  projectRole: string;
};

function DayDetail({
  visible,
  onCancel,
  selectedDay,
  projectRole,
}: DaydetailProps) {
  if (!visible) return null;

  return (
    <div
      className={"background" + (visible ? " visible" : "")}
      onClick={(event) => {
        onCancel();
      }}
    >
      <div
        className="dayDetail shadowBox"
        onClick={(event) => event.stopPropagation()}
      >
        {/*이벤트 캡쳐링 방지 */}
        <Schedule selectedDay={selectedDay} projectRole={projectRole} />
        <div className="dayDetailRight">
          <Record selectedDay={selectedDay} />

          <button className="cancel-btn btn" onClick={onCancel}>
            취소
          </button>
        </div>
      </div>
    </div>
  );
}

export default DayDetail;
