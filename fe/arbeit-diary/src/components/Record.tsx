import React, { createRef, useState } from "react";
import "../css/components/Record.css";
import { MdAdd, MdRestoreFromTrash } from "react-icons/md";
import { shallowEqual, useDispatch, useSelector } from "react-redux";
import { RootState } from "../module";
import { addDetail, dayIssueType, removeDetail } from "../module/Calendar";
import { calendarupdateapi } from "../api/UserApi";

type RecordProps = {
  selectedDay: string;
};

function Record({ selectedDay }: RecordProps) {
  const dispatch = useDispatch();
  const calendar = useSelector((state: RootState) => state.CalenderInfo)[0];
  const [saveflag, setsaveflag] = useState(false);
  const day =
    selectedDay.slice(0, 4) +
    "-" +
    selectedDay.slice(6, 8) +
    "-" +
    selectedDay.slice(10, 12);
  const today = new Date(day).getDay();
  const week = new Array(
    "일요일",
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일"
  ); //요일구하기
  const currentdate = selectedDay.replace(/[^0-9]/g, "").slice(2); //현재날짜출력
  const issues = useSelector(
    //users목록 추출
    (state: RootState) => state.CalenderInfo,
    shallowEqual
  )[0].dates.filter((date) => date.date === currentdate)[0].dayIssues;
  const user = useSelector((state: RootState) => state.Userinfo)[0];

  const DayIssue = ({ name, time, text }: dayIssueType, userName: string) => {
    return (
      <div className="issuebox">
        <div>{text}</div>
        <div className="issuedetail">
          <div>{name}&nbsp;</div>
          <div>{time}</div>
          <MdRestoreFromTrash
            className="trash"
            onClick={() => {
              dispatch(removeDetail({ name: userName, text }));
              setsaveflag(true);
            }}
          />
        </div>
      </div>
    );
  };

  const date = new Date();
  let currenttime = String(date.getHours()) + ":" + String(date.getMinutes());

  const onsubmitdetail = async (e: any) => {
    e.preventDefault();

    await dispatch(
      addDetail({
        date: currentdate,
        text: e.target.addDetail.value,
        name: user.userName,
        time: currenttime,
        userId: user.userId,
      })
    );
    e.target.addDetail.value = "";
    setsaveflag(true);
  };

  if (saveflag) {
    const token = localStorage.getItem("token");

    calendarupdateapi(token === null ? "" : token, calendar);

    setsaveflag(false);
  }

  return (
    <div className="record">
      <div className="record-header">
        <div className="selectedDay">{selectedDay}</div>
        <div className="day-of-week">{week[today]}</div>
      </div>
      <div className="record-body">
        {issues.map((issue, index) => {
          return <div key={index}>{DayIssue(issue, user.userName)}</div>;
        })}
      </div>
      <form className="recordform" onSubmit={onsubmitdetail}>
        <textarea
          id="addDetail"
          className="inputBox"
          placeholder="특이사항을 입력하세요"
        />
        <button className="plusBox">
          <MdAdd type="submit" />
        </button>
      </form>
    </div>
  );
}

export default Record;
