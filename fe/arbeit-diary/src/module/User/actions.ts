//import { deprecated } from "typesafe-actions";

import { Usertype } from ".";

export const ADD_PROJECT = "User/ADD_PROJECT" as const;
export const TOGGLE_NAME = "User/TOGGLE_NAME" as const;
export const GET_USERINFO = "User/GET_USERINFO" as const;
//export const getUser = deprecated.createStandardAction(GET_USER)();

export const addProject = (id: string) => ({
  type: ADD_PROJECT,
  payload: id,
});

export const toggleName = (name: string, projectId: string) => ({
  type: TOGGLE_NAME,
  name,
  projectId,
});

export const get_userinfo = (userinfo: Usertype) => ({
  type: GET_USERINFO,
  payload: userinfo,
});
