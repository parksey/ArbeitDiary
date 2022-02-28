import {
  addDetailPropsType,
  removeDetailPropsType,
  toggleDetailPropsType,
  addScheduleUserPropsType,
  removeScheduleUserPropsType,
  toggleFixedSchedulePropsType,
  CalenderType,
} from "./types";

export const TEST = "Calender/TEST" as const;
export const ADD_DATE = "Calendar/ADD_DATE" as const;
export const ADD_DETAIL = "Calendar/ADD_DETAIL" as const;
export const REMOVE_DETAIL = "Calendar/REMOVE_DETAIL" as const;
export const TOGGLE_DETAIL = "Calendar/TOGGLE_DETAIL" as const;
export const ADD_SCHEDULE_USER = "Calendar/ADD_SCHEDULE_USER" as const;
export const REMOVE_SCHEDULE_USER = "Calendar/REMOVE_SCHEDULE_USER" as const;
export const TOGGLE_FIXED_SCHEDULE = "Calendar/TOGGLE_FIXED_SCHEDULE" as const;
export const GET_CALENDARINFO = "Calendar/GET_CALENDARINFO" as const;
//export const getUser = deprecated.createStandardAction(GET_USER)();

export const onTest = (id: string) => ({
  type: TEST,
  payload: id,
});

export const addDate = (date: string) => ({
  type: ADD_DATE,
  payload: date,
});

export const addDetail = ({
  date,
  text,
  name,
  time,
  userId,
}: addDetailPropsType) => ({
  type: ADD_DETAIL,
  payload: {
    date,
    text,
    name,
    time,
    userId,
  },
});

export const removeDetail = ({ name, text }: removeDetailPropsType) => ({
  type: REMOVE_DETAIL,
  payload: {
    name,
    text,
  },
});

export const toggleDetail = ({
  date,
  index,
  name,
  userId,
}: toggleDetailPropsType) => ({
  type: TOGGLE_DETAIL,
  payload: {
    date,
    index,
    name,
    userId,
  },
});

export const addScheduleUser = ({
  date,
  name,
  userId,
}: addScheduleUserPropsType) => ({
  type: ADD_SCHEDULE_USER,
  payload: {
    date,
    name,
    userId,
  },
});

export const removeScheduleUser = ({
  date,
  name,
  userId,
}: removeScheduleUserPropsType) => ({
  type: REMOVE_SCHEDULE_USER,
  payload: {
    date,
    name,
    userId,
  },
});

export const toggleFixedSchedule = ({
  userId,
  dayId,
  index,
}: toggleFixedSchedulePropsType) => ({
  type: TOGGLE_FIXED_SCHEDULE,
  payload: { userId, dayId, index },
});

export const getCalendarinfo = (calendarinfo: CalenderType) => ({
  type: GET_CALENDARINFO,
  payload: calendarinfo,
});
