import moment from "moment";
import React, { useState } from "react";
import "../css/components/Calendar.css";
import { MdChevronRight, MdChevronLeft } from "react-icons/md";

type CalendarProps = {
  onConfirm: () => void;
  onConfirmDay: (days: moment.Moment) => void;
};

function Calendar({ onConfirm, onConfirmDay }: CalendarProps) {
  const [getMoment, setMoment] = useState(moment());
  const today = getMoment; // today == moment()   입니다.

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

              if (moment().format("YYYYMMDD") === days.format("YYYYMMDD")) {
                return (
                  <div
                    className="calendarDay"
                    key={index}
                    style={{
                      backgroundColor: "var(--main-color)",
                      color: "#fff",
                    }}
                    onClick={() => {
                      onConfirm();
                      onConfirmDay(days);
                    }}
                  >
                    {days.format("D")}
                  </div>
                );
              } else if (days.format("MM") !== today.format("MM")) {
                return (
                  <div
                    className="calendarDay"
                    key={index}
                    style={{ backgroundColor: "#d2d2d2ee" }}
                    onClick={() => {
                      onConfirm();
                      onConfirmDay(days);
                    }}
                  >
                    {days.format("D")}
                  </div>
                );
              } else {
                return (
                  <div
                    className="calendarDay"
                    key={index}
                    onClick={() => {
                      onConfirm();
                      onConfirmDay(days);
                    }}
                  >
                    {days.format("D")}
                  </div>
                );
              }
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
