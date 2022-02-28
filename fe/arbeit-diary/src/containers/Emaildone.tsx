import React from "react";
import { useLocation } from "react-router";
import { Emailrequestapi } from "../api/UserApi";
import "../css/containers/Emaildone.css";

import { MdSentimentVerySatisfied } from "react-icons/md";
function Emaildone() {
  const location = useLocation();
  const id = location.search.slice(4);
  console.log(id);

  Emailrequestapi({ id });

  return (
    <div className="Emaildone">
      <MdSentimentVerySatisfied className="smile" />
      <div className="loading">로딩중 . . .</div>
    </div>
  );
}

export default Emaildone;
