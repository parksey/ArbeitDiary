import React from "react";
import "../css/components/UserList.css";

import { MdDone, MdAdd } from "react-icons/md";

import classNames from "classnames";

type UserProps = {
  done: boolean;
  text: string;
};

function User({ done, text }: UserProps) {
  return (
    <div className="User">
      <div className={classNames("checkBox", { checkBoxDone: done })}>
        {done && <MdDone />}
      </div>
      <div className={classNames("userText", { userTextDone: done })}>
        {text}
      </div>
    </div>
  );
}

function UserList() {
  return (
    <div className="UserList">
      <div className="UserList-title">사용자 목록</div>
      <User done={true} text={"박세연"} />
      <User done={false} text={"정민규"} />
      <User done={true} text={"박세연"} />
      <User done={false} text={"정민규"} />
      <User done={true} text={"박세연"} />
      <User done={false} text={"정민규"} />
      <User done={true} text={"박세연"} />
      <User done={false} text={"정민규"} />
      <div className="plusBox">
        <MdAdd />
      </div>
    </div>
  );
}

export default UserList;
