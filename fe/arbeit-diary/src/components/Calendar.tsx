import moment from "moment";
import React, { useState } from "react";
import "../css/components/Calendar.css";
import { MdChevronRight, MdChevronLeft } from "react-icons/md";
import { useSelector } from "react-redux";
import { RootState } from "../module";
import { projectType } from "../module/User";

type CalendarProps = {
  onConfirm: () => void;
  onConfirmDay: (days: moment.Moment) => void;
  constproject: projectType;
};

function Calendar({ onConfirm, onConfirmDay, constproject }: CalendarProps) {
  const calendar = useSelector((state: RootState) => state.CalenderInfo)[0];
  const dates = calendar.dates;
  const trueUserList = constproject.userList //참으로 표시한 userList
    .filter((user) => user.done === true)
    .map((user) => user.userName);

  const [getMoment, setMoment] = useState(moment());
  const today = getMoment; // today == moment()

  const firstWeek = today.clone().startOf("month").week();
  const lastWeek =
    today.clone().endOf("month").week() === 1
      ? 53
      : today.clone().endOf("month").week();

  const calendarArr = () => {
    let result: any = []; //type 맞춰줘야함
    let week = firstWeek;

    for (week; week <= lastWeek; week++) {
      result = result.concat(
        <div className="calendarWeek" key={week}>
          {Array(7)
            .fill(0)
            // eslint-disable-next-line no-loop-func
            .map((data, index) => {
              let days = today
                .clone()
                .startOf("year")
                .week(week)
                .startOf("week")
                .add(index, "day");

              let workflag = false;

              try {
                dates
                  .filter((date) => date.date === days.format("YYMMDD"))[0]
                  .users.map((user) => {
                    for (const truename in trueUserList) {
                      if (user.name === trueUserList[truename]) {
                        if (
                          user.worktime !==
                          "000000000000000000000000000000000000000000000000"
                        ) {
                          workflag = true;
                        }
                      }
                    }
                  });
              } catch (error) {
                workflag = false;
              }

              //console.log(days.format("YYMMDD"));

              const daycheck =
                days.format("MM") !== today.format("MM")
                  ? " gray"
                  : moment().format("YYYYMMDD") === days.format("YYYYMMDD")
                  ? " today"
                  : "";

              return (
                <div
                  className={"calendarDay" + daycheck}
                  id={workflag ? "check" : ""}
                  key={index}
                  onClick={() => {
                    onConfirm();
                    onConfirmDay(days);
                  }}
                >
                  {days.format("D")}
                </div>
              );
            })}
        </div>
      );
    }
    return result;
  };

  return (
    <div className="calendar">
      <div className="control">
        <button
          className="btn btn-calendar btn-beforeMonth"
          onClick={() => {
            setMoment(getMoment.clone().subtract(1, "month"));
          }}
        >
          <MdChevronLeft />
        </button>
        <span>{today.format("YYYY 년 MM 월")}</span>
        <button
          className="btn btn-calendar btn-afterMonth"
          onClick={() => {
            setMoment(getMoment.clone().add(1, "month"));
          }}
        >
          <MdChevronRight />
        </button>
      </div>

      <div className="calendarBody">
        <div className="calendarDays">
          <div className="calendarDayofWeek sunday">SUN</div>
          <div className="calendarDayofWeek">MON</div>
          <div className="calendarDayofWeek">THU</div>
          <div className="calendarDayofWeek">WEN</div>
          <div className="calendarDayofWeek">THU</div>
          <div className="calendarDayofWeek">FRI</div>
          <div className="calendarDayofWeek saturday">SAT</div>
        </div>
        {calendarArr()}
      </div>
    </div>
  );
}

export default Calendar;
