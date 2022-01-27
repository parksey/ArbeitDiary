import React from "react";
import { Link } from "react-router-dom";
import "../css/containers/Login.css";

function Login() {
  return (
    <>
      <div className="Login-body">
        <link
          rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossOrigin="anonymous"
        />
        <form className="form-signin" id="form-login">
          <h1 className="h3 mb-3 font-weight-normal">로그인 정보 입력</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="email"
            id="email"
            className="form-control"
            placeholder="이메일 주소"
            required
            autoFocus
          />
          <label htmlFor="inputPassword" className="sr-only">
            Password
          </label>
          <input
            type="password"
            id="password"
            className="form-control"
            placeholder="비밀번호"
            required
          />
          <button className="btn-login" type="submit">
            로그인
          </button>

          <div className="find">
            <Link to="#">아이디 찾기</Link>
            <Link to="#">비밀번호 찾기</Link>
            <Link to="/Regist">회원가입</Link>
          </div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
        <script
          src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
          integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
          crossOrigin="anonymous"
        ></script>
        <script
          src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
          integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
          crossOrigin="anonymous"
        ></script>
        <script
          src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
          integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
          crossOrigin="anonymous"
        ></script>
      </div>
    </>
  );
}

export default Login;
