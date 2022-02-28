import { createReducer } from "typesafe-actions";
import { ADD_PROJECT, TOGGLE_NAME, GET_USERINFO } from "./actions";
import { UserAction, User } from "./types";

const initialState: User = [
  {
    userId: "1",
    userName: "정민규",
    phone: "01022348115",
    projects: [
      {
        projectId: "1",
        joinId: "",
        projectName: "서브웨이 연수점",
        calendarId: "1",

        userList: [
          { userName: "정민규", userId: "supersfel@naver.com", done: false },
          {
            userName: "박세연",
            userId: "parkseyeon99@naver.com",
            done: true,
          },
        ],
      },
      {
        projectId: "2",
        projectName: "맘스터치 개봉점",
        calendarId: "2",
        userList: [
          { userName: "짭민규", userId: "supersfel@naver.com", done: true },
          {
            userName: "짭세연",
            userId: "parkseyeon99@naver.com",
            done: false,
          },
        ],
      },
    ],
  },
];

const Userinfo = createReducer<User, UserAction>(initialState, {
  [ADD_PROJECT]: (state, action) =>
    state.map((user) => ({
      ...user,
      projects: user.projects.concat({
        projectId: "3",
        projectName: action.payload,
        calendarId: "3",
        userList: [
          {
            userName: user.userName,
            userId: "supersfel@naver.com",
            done: true,
          },
        ],
      }),
    })),
  [TOGGLE_NAME]: (state, action) =>
    state.map((user) => ({
      ...user,
      projects: user.projects.map((project) =>
        project.projectId === action.projectId
          ? {
              ...project,
              userList: project.userList.map((user) =>
                user.userName === action.name
                  ? {
                      ...user,
                      done: !user.done,
                    }
                  : {
                      ...user,
                    }
              ),
            }
          : { ...project }
      ),
    })),
  [GET_USERINFO]: (state, action) => state.map((user) => action.payload),
});

export default Userinfo;
