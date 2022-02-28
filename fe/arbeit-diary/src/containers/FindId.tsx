import React, { useState } from "react";
import { Link } from "react-router-dom";
import { findidapi } from "../api/UserApi";
import Header from "../components/Header";

function FindId() {
  const [visible, setvisible] = useState(false);
  const [IdList, setIdList] = useState([]);
  const onCancel = () => {
    setvisible(false);
  };

  const onSubmit = async (e: any) => {
    e.preventDefault();
    const list = await findidapi({
      userName: e.target.userName.value,
      userPhone: e.target.userPhone.value,
    });
    setIdList(list);
    setvisible(true);
  };

  function IdlistModal() {
    return (
      <div
        className={"background" + (visible ? " visible" : "")}
        onClick={(event) => {
          onCancel();
        }}
      >
        <div
          className="emailmodal"
          onClick={(event) => event.stopPropagation()}
        >
          아이디 목록
          {IdList.map((text, index) => {
            return <div key={index}>{text}</div>;
          })}
          <Link to="/">홈 화면으로 이동</Link>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="Login-body">
        <link
          rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossOrigin="anonymous"
        />
        <form className="form-signin" id="form-login" onSubmit={onSubmit}>
          <h1 className="h3 mb-3 font-weight-normal">아이디 찾기</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="text"
            id="userName"
            className="form-control"
            placeholder="이름"
            required
            autoFocus
          />
          <label htmlFor="inputPassword" className="sr-only">
            Password
          </label>
          <input
            type="text"
            id="userPhone"
            className="form-control"
            placeholder="핸드폰 번호"
            required
          />

          <button className="btn-login" type="submit">
            아이디 찾기
          </button>

          <div className="find">
            <Link to="/login">로그인</Link>
            <Link to="/findpassword">비밀번호 찾기</Link>
            <Link to="/regist">회원가입</Link>
          </div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
      <IdlistModal />
    </>
  );
}

export default FindId;
