import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import CheckToken from "../api/CheckToken";
import "../css/components/Header.css";

function Header() {
  const [ScrollY, setScrollY] = useState(0); // window 의 pageYOffset값을 저장
  const [ScrollActive, setScrollActive] = useState(false);
  const history = useHistory();
  function handleScroll() {
    if (ScrollY > 10) {
      setScrollY(window.pageYOffset);
      setScrollActive(true);
    } else {
      setScrollY(window.pageYOffset);
      setScrollActive(false);
    }
  }
  useEffect(() => {
    function scrollListener() {
      window.addEventListener("scroll", handleScroll);
    } //  window 에서 스크롤을 감시 시작
    scrollListener(); // window 에서 스크롤을 감시
    return () => {
      window.removeEventListener("scroll", handleScroll);
    }; //  window 에서 스크롤을 감시를 종료
  });

  const onlogout = () => {
    if (CheckToken()) {
      localStorage.clear();
      history.go(0);
    }
  };

  return (
    <>
      <header className={"Header" + (ScrollActive ? " onScrolled" : "")}>
        <Link to="/" className="left">
          <div className="logo-icon"></div>
          <div className="logo"></div>
        </Link>
        <Link
          to={CheckToken() ? "/" : "/login"}
          onClick={onlogout}
          className="right"
        >
          {CheckToken() ? "로그아웃" : "로그인"}
        </Link>
      </header>
    </>
  );
}

export default Header;
