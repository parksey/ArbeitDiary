import { createReducer } from "typesafe-actions";
import { CalenderAction, CalendersType } from "./types";
import {
  TOGGLE_DETAIL,
  REMOVE_DETAIL,
  ADD_DATE,
  ADD_DETAIL,
  ADD_SCHEDULE_USER,
  REMOVE_SCHEDULE_USER,
  TOGGLE_FIXED_SCHEDULE,
  GET_CALENDARINFO,
} from "./actions";

const initialState: CalendersType = [
  {
    calendarId: "6",
    projectId: "13",
    projectName: "맘스터치 오류점",
    userList: [
      {
        userId: "parkseyeon99@naver.com",
        name: "박세연",
        fixedtimes: [
          {
            dayId: "MONDAY",
            worktime: "000000000000000000011111111000000000000000000000",
          },
          {
            dayId: "TUESDAY",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "WEDNESDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "THURSDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "FRIDAY",
            worktime: "000000001111111100000000000000000000000000000000",
          },
          {
            dayId: "SATURDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "SUNDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
        ],
      },
      {
        userId: "supersfel@naver.com",
        name: "정민규",
        fixedtimes: [
          {
            dayId: "MONDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "TUESDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "WEDNESDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "THURSDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "FRIDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "SATURDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "SUNDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
        ],
      },
      {
        userId: "user8@naver.com",
        name: "고민수",
        fixedtimes: [
          {
            dayId: "MONDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "TUESDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "WEDNESDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "THURSDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "FRIDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "SATURDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
          {
            dayId: "SUNDAY",
            worktime: "000000000000000000000000000000000000000000000000",
          },
        ],
      },
    ],
    dates: [
      {
        date: "220212",
        dateId: "155",
        dayIssues: [
          {
            name: "박세연",
            text: "안녕하세요",
            time: "14:20",
            userId: "parkseyeon99@naver.com",
          },
          {
            name: "박세연",
            text: "Hello",
            time: "14:20",
            userId: "parkseyeon99@naver.com",
          },
          {
            name: "고철수",
            text: "얍얍",
            time: "14:21",
            userId: "user3@naver.com",
          },
        ],
        dayId: "SATURDAY",
        users: [],
      },
      {
        date: "220213",
        dateId: "156",
        dayIssues: [],
        dayId: "SUNDAY",
        users: [],
      },
      {
        date: "220214",
        dateId: "157",
        dayIssues: [],
        dayId: "MONDAY",
        users: [],
      },
      {
        date: "220215",
        dateId: "158",
        dayIssues: [],
        dayId: "TUESDAY",
        users: [],
      },
      {
        date: "220216",
        dateId: "159",
        dayIssues: [],
        dayId: "WEDNESDAY",
        users: [],
      },
      {
        date: "220217",
        dateId: "160",
        dayIssues: [],
        dayId: "THURSDAY",
        users: [],
      },
      {
        date: "220218",
        dateId: "161",
        dayIssues: [],
        dayId: "FRIDAY",
        users: [],
      },
      {
        date: "220219",
        dateId: "162",
        dayIssues: [],
        dayId: "SATURDAY",
        users: [],
      },
      {
        date: "220220",
        dateId: "163",
        dayIssues: [],
        dayId: "SUNDAY",
        users: [],
      },
      {
        date: "220221",
        dateId: "164",
        dayIssues: [],
        dayId: "MONDAY",
        users: [],
      },
      {
        date: "220222",
        dateId: "165",
        dayIssues: [],
        dayId: "TUESDAY",
        users: [],
      },
      {
        date: "220223",
        dateId: "166",
        dayIssues: [],
        dayId: "WEDNESDAY",
        users: [],
      },
      {
        date: "220224",
        dateId: "167",
        dayIssues: [],
        dayId: "THURSDAY",
        users: [],
      },
      {
        date: "220225",
        dateId: "168",
        dayIssues: [],
        dayId: "FRIDAY",
        users: [],
      },
      {
        date: "220226",
        dateId: "169",
        dayIssues: [],
        dayId: "SATURDAY",
        users: [],
      },
      {
        date: "220227",
        dateId: "170",
        dayIssues: [],
        dayId: "SUNDAY",
        users: [],
      },
      {
        date: "220228",
        dateId: "171",
        dayIssues: [],
        dayId: "MONDAY",
        users: [],
      },
      {
        date: "220301",
        dateId: "172",
        dayIssues: [],
        dayId: "TUESDAY",
        users: [],
      },
      {
        date: "220302",
        dateId: "173",
        dayIssues: [],
        dayId: "WEDNESDAY",
        users: [],
      },
      {
        date: "220303",
        dateId: "174",
        dayIssues: [],
        dayId: "THURSDAY",
        users: [],
      },
      {
        date: "220304",
        dateId: "175",
        dayIssues: [],
        dayId: "FRIDAY",
        users: [],
      },
      {
        date: "220305",
        dateId: "176",
        dayIssues: [],
        dayId: "SATURDAY",
        users: [],
      },
      {
        date: "220306",
        dateId: "177",
        dayIssues: [],
        dayId: "SUNDAY",
        users: [],
      },
      {
        date: "220307",
        dateId: "178",
        dayIssues: [],
        dayId: "MONDAY",
        users: [],
      },
      {
        date: "220308",
        dateId: "179",
        dayIssues: [],
        dayId: "TUESDAY",
        users: [],
      },
      {
        date: "220309",
        dateId: "180",
        dayIssues: [],
        dayId: "WEDNESDAY",
        users: [],
      },
      {
        date: "220310",
        dateId: "181",
        dayIssues: [],
        dayId: "THURSDAY",
        users: [],
      },
      {
        date: "220311",
        dateId: "182",
        dayIssues: [],
        dayId: "FRIDAY",
        users: [],
      },
      {
        date: "220312",
        dateId: "183",
        dayIssues: [],
        dayId: "SATURDAY",
        users: [],
      },
      {
        date: "220313",
        dateId: "184",
        dayIssues: [],
        dayId: "SUNDAY",
        users: [],
      },
    ],
  },
];

const CalenderInfo = createReducer<CalendersType, CalenderAction>(
  initialState,
  {
    [ADD_DATE]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.concat({
          date: action.payload,
          dateId: "3",
          dayId: "SATURDAY",
          users: [],
          dayIssues: [],
        }),
      })),
    [ADD_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          dayIssues:
            date.date === action.payload.date
              ? date.dayIssues.concat({
                  name: action.payload.name,
                  time: action.payload.time,
                  text: action.payload.text,
                  userId: action.payload.userId,
                })
              : date.dayIssues,
        })),
      })),
    [REMOVE_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          dayIssues: date.dayIssues.filter(
            (dayissue) =>
              dayissue.name !== action.payload.name ||
              dayissue.text !== action.payload.text
          ),
        })),
      })),
    [TOGGLE_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.map((user) =>
                  user.userId === action.payload.userId
                    ? user.worktime[action.payload.index] === "1"
                      ? {
                          ...user,
                          worktime:
                            user.worktime.slice(0, action.payload.index) +
                            "0" +
                            user.worktime.slice(action.payload.index + 1),
                        }
                      : {
                          ...user,
                          worktime:
                            user.worktime.slice(0, action.payload.index) +
                            "1" +
                            user.worktime.slice(action.payload.index + 1),
                        }
                    : { ...user }
                )
              : date.users,
        })),
      })),
    [ADD_SCHEDULE_USER]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.concat({
                  name: action.payload.name,
                  userId: action.payload.userId,
                  worktime: "000000000000000000000000000000000000000000000000",
                })
              : date.users,
        })),
      })),
    [REMOVE_SCHEDULE_USER]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.filter(
                  (user) => user.userId !== action.payload.userId
                )
              : date.users,
        })),
      })),
    [TOGGLE_FIXED_SCHEDULE]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        userList: calendar.userList.map((user) =>
          user.userId === action.payload.userId
            ? {
                ...user,
                fixedtimes: user.fixedtimes.map((day) =>
                  day.dayId === action.payload.dayId
                    ? day.worktime[action.payload.index] === "1"
                      ? {
                          ...day,
                          worktime:
                            day.worktime.slice(0, action.payload.index) +
                            "0" +
                            day.worktime.slice(action.payload.index + 1),
                        }
                      : {
                          ...day,
                          worktime:
                            day.worktime.slice(0, action.payload.index) +
                            "1" +
                            day.worktime.slice(action.payload.index + 1),
                        }
                    : { ...day }
                ),
              }
            : { ...user }
        ),
      })),
    [GET_CALENDARINFO]: (state, action) => state.map(() => action.payload),
  }
);

export default CalenderInfo;
