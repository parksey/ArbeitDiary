import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { MdAdd } from "react-icons/md";
import "../css/components/Schedule.css";
import { RootState } from "../module";
import {
  addDate,
  addScheduleUser,
  removeScheduleUser,
  toggleDetail,
} from "../module/Calendar";

type ScheduleProps = {
  selectedDay: string;
  projectRole: string;
};

type UserScheduleProps = {
  worktimes: string;
  username: string;
};

function Schedule({ selectedDay, projectRole }: ScheduleProps) {
  const dispatch = useDispatch();
  const [visible, setvisible] = useState(false); //modal 변수

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

  const currentdate = selectedDay.replace(/[^0-9]/g, "").slice(2); //현재날짜출력

  try {
    //user목록 없을때 임시로 추출
    // const test = useSelector(
    //   //users목록 추출
    //   (state: RootState) => state.CalenderInfo
    // )[0].dates.filter((date) => date.date === currentdate)[0].users;

    const test = useSelector(
      (state: RootState) => state.CalenderInfo
    )[0].dates.filter((date) => date.date === currentdate)[0].users;
  } catch (error) {
    dispatch(addDate(currentdate));
  }

  const Calendar = useSelector((state: RootState) => state.CalenderInfo)[0];

  const users = Calendar.dates.filter((date) => date.date === currentdate)[0]
    .users;

  const currentuserList = users.map((user) => user.name); //현재 스케쥴이 없는 유저의 리스트를 만드는 부분
  const everyuserList = Calendar.userList.map((user) => user.name);
  const exceptuserList = everyuserList.filter(
    (user) => !currentuserList.includes(user)
  );

  const finduserId = (name: string) => {
    return Calendar.userList.filter((user) => user.name === name)[0].userId;
  };

  const onsubmitScheduleUser = (e: any) => {
    e.preventDefault();
    dispatch(
      addScheduleUser({
        date: currentdate,
        name: e.target.adduser.value,
        userId: finduserId(e.target.adduser.value),
      })
    );
    e.target.adduser.value = "";
    setvisible(false);
    console.log(Calendar);
  };

  const onremoveScheduleUser = (name: string) => {
    dispatch(
      removeScheduleUser({
        date: currentdate,
        name,
        userId: finduserId(name),
      })
    );
  };

  const UserShedule = ({ worktimes, username }: UserScheduleProps) => {
    const worktimelst = worktimes.split("");

    const onclicktime = (index: number) => {
      if (projectRole === "MASTER") {
        dispatch(
          toggleDetail({
            date: currentdate,
            name: username,
            index,
            userId: finduserId(username),
          })
        );
      }
    };
    return (
      <div className="worktimes">
        {worktimelst.map((worktime, index) => {
          return worktime === "1" ? (
            <div
              className="time work touch"
              key={index}
              onClick={() => {
                onclicktime(index);
              }}
            >
              {username}
            </div>
          ) : (
            <div
              className="time touch"
              key={index}
              onClick={() => {
                onclicktime(index);
              }}
            ></div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="schedule shadowBox">
      <div className="times column-line">
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

      {users.map((user, index) => {
        return (
          <div className="column-line" key={index}>
            <div className="first-row-line">
              {user.name}
              {projectRole === "MASTER" ? (
                <MdAdd
                  className="removeScheduleuserbtn"
                  onClick={() => {
                    onremoveScheduleUser(user.name);
                  }}
                />
              ) : (
                ""
              )}
            </div>
            <UserShedule worktimes={user.worktime} username={user.name} />
          </div>
        );
      })}

      {projectRole === "MASTER" ? (
        <div className="column-line user-plus-btn">
          <MdAdd
            className="addbtn scheduleAdd"
            onClick={() => {
              exceptuserList.length === 0
                ? alert("추가할 사용자가 없습니다!")
                : setvisible(true);
            }}
          />
        </div>
      ) : (
        ""
      )}

      <form
        className={
          "ScheduleUserAddForm shadowBox" + (visible ? " visible" : "")
        }
        onSubmit={onsubmitScheduleUser}
      >
        <input
          type="text"
          list="exceptUserList"
          className="inputScheduleUser"
          placeholder="추가할 사용자 이름을 선택해주세요!"
          id="adduser"
          autoComplete="off"
        ></input>
        <datalist id="exceptUserList">
          {exceptuserList.map((name, index) => {
            return <option key={index}>{name}</option>;
          })}
        </datalist>
        <div className="ScheduleFormRight">
          <button className="adduserbtn btn">추가</button>
          <div
            className="adduserbtn btn"
            onClick={() => {
              setvisible(false);
            }}
          >
            취소
          </div>
        </div>
      </form>
    </div>
  );
}

export default Schedule;
