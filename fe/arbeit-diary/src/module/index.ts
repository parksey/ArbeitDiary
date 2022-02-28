import { combineReducers } from "redux";
import Userinfo from "./User";
import CalenderInfo from "./Calendar/reducer";

const rootReducer = combineReducers({
  Userinfo,
  CalenderInfo,
});

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;


