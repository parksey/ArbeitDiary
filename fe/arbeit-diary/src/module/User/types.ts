import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type UserAction = ActionType<typeof actions>;

export type UserListType = {
  done: boolean;
  userName: string;
  userId: string;
  phone?: string;
};

export type projectType = {
  projectId: string;
  joinId?: string;
  projectRole?: string;
  projectRegDt?: string;

  projectName: string;
  calendarId: string;
  userList: UserListType[];
};

export type Usertype = {
  userId: string;
  userName: string;
  phone: string;
  projects: projectType[];
};

export type User = Usertype[];
