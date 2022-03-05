import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useLocation } from "react-router";
import { getCalendarApi, sendfixedscheduleapi } from "../api/UserApi";
import "../css/components/FixedSchedule.css";
import { RootState } from "../module";
import { toggleFixedSchedule } from "../module/Calendar";

type fixedScheduleProps = {
  userId: string;
  FixedSchedulevisible: boolean;
  setFixedSchedulevisible: (value: boolean) => void;
};

type FixedscheduletimeProps = {
  worktimes: string;
  dayId: string;
};

function FixedSchedule({
  userId,
  FixedSchedulevisible,
  setFixedSchedulevisible,
}: fixedScheduleProps) {
  const dispatch = useDispatch();
  const history = useHistory();
  const location = useLocation();
  const projectId = location.search.slice(11);
  const calendar = useSelector((state: RootState) => state.CalenderInfo)[0];
  const userList = calendar.userList;

  const user = userList.filter((user) => user.userId === userId)[0];

  if (!FixedSchedulevisible) return <></>;

  let hours = []; //00:00 ~ 23:30까지 생성
  for (let i = 0; i < 24; i++) {
    i < 10 ? hours.push("0" + String(i)) : hours.push(String(i));
  }
  const minutes = ["00", "30"];
  let times: string[] = [];
  hours.map((hour) => {
    minutes.map((minute) => {
      times.push(hour + ":" + minute);
    });
  });

  const onSendfixedschedule = async () => {
    const token = localStorage.getItem("token");
    await sendfixedscheduleapi(token === null ? "" : token, calendar).then(
      () => {
        //window.location.replace(`/project/?projectId=${projectId}`);
        history.push(`/oldproject`);
        setFixedSchedulevisible(false);
      }
    );
    setFixedSchedulevisible(false);
  };

  const FixedScheduletime = ({ worktimes, dayId }: FixedscheduletimeProps) => {
    const worktimelst = worktimes.split("");
    const [dragflag, setdragflag] = useState(false);
    const [draglst, setdraglst] = useState<number[]>([]);

    return (
      <div className="worktimes">
        {worktimelst.map((worktime, index) => {
          return worktime === "1" ? (
            <div
              className="time work touch"
              key={index}
              style={{ background: "var(--main-color)" }}
              onClick={() =>
                dispatch(
                  toggleFixedSchedule({
                    userId,
                    dayId,
                    index,
                  })
                )
              }
              onMouseDown={() => {
                setdragflag(true);
                setdraglst(draglst.concat([index]));
              }}
              onMouseUp={() => {
                setdragflag(false);
                draglst.map((index) =>
                  dispatch(
                    toggleFixedSchedule({
                      userId,
                      dayId,
                      index,
                    })
                  )
                );
                setdraglst([]);
              }}
              onMouseEnter={() =>
                dragflag ? setdraglst(draglst.concat([index])) : ""
              }
            ></div>
          ) : (
            <div
              className="time touch"
              key={index}
              onClick={() =>
                dispatch(
                  toggleFixedSchedule({
                    userId,
                    dayId,
                    index,
                  })
                )
              }
              onMouseDown={() => {
                setdragflag(true);
                setdraglst(draglst.concat([index]));
              }}
              onMouseUp={() => {
                setdragflag(false);
                draglst.map((index) =>
                  dispatch(
                    toggleFixedSchedule({
                      userId,
                      dayId,
                      index,
                    })
                  )
                );
                setdraglst([]);
              }}
              onMouseEnter={() =>
                dragflag ? setdraglst(draglst.concat([index])) : ""
              }
            ></div>
          );
        })}
      </div>
    );
  };

  return (
    <>
      <div
        className="fixedSchedule"
        onClick={() => setFixedSchedulevisible(false)}
      >
        <div
          className="settingBox shadowBox "
          onClick={(event) => event.stopPropagation()}
        >
          {user.name} 님의 고정근무 설정
          <div className="settingBoxUp schedule shadowBox">
            <div className="times column-line fixedtimeline">
              <div className="first-row-line">시간</div>
              <div className="worktimes">
                {times.map((time, index) => {
                  return (
                    <div className="time" key={index}>
                      {time}
                    </div>
                  );
                })}
              </div>
            </div>
            {user.fixedtimes.map((day, index) => {
              return (
                <div className="times columnline fixedtimeline" key={index}>
                  <div className="first-row-line">
                    {day.dayId.slice(0, 3).toUpperCase()}
                  </div>
                  <FixedScheduletime
                    worktimes={day.worktime}
                    dayId={day.dayId}
                  />
                </div>
              );
            })}
          </div>
          <div className="settingBoxDown">
            <div className="settingBoxbtn btn" onClick={onSendfixedschedule}>
              저장
            </div>
            <div
              className="settingBoxbtn btn"
              onClick={() => {
                setFixedSchedulevisible(false);
                history.go(0);
              }}
            >
              취소
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default FixedSchedule;
