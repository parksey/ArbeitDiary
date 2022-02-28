import React, { CSSProperties, useState } from "react";
import { useHistory, useLocation } from "react-router";
import { resetpasswordapi } from "../api/UserApi";

function ResetPassword() {
  const location = useLocation();
  const history = useHistory();
  const [password, setPassword] = useState(""); // 비밀번호 일치확인
  const [confirmPassword, setConfirmPassword] = useState("");
  const [correctpassword, setcorrectpassword] = useState(false);
  const passwordType = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;

  const onSubmit = (e: any) => {
    e.preventDefault();
    if (passwordType.test(e.target.password.value)) {
      if (password === confirmPassword) {
        resetpasswordapi(
          {
            userPassword: e.target.password.value,
          },
          location.search.slice(4)
        ).then(() => history.push("/"));
      } else {
        alert("비밀번호가 일치하지 않습니다");
      }
    } else {
      alert("비밀번호 양식을 확인해주세요");
    }
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    passwordType.test(e.target.value)
      ? setcorrectpassword(false)
      : setcorrectpassword(true);
    setPassword(e.target.value);
  };
  const onChangeConfirmPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setConfirmPassword(e.target.value);
  };

  const uncorrectstyle: CSSProperties = {
    border: password !== confirmPassword ? "4px solid var(--main-color)" : "",
  };

  const commentpassword: CSSProperties = {
    display: correctpassword ? "block" : "none",
  };

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
          <h1 className="h3 mb-3 font-weight-normal">새 비밀번호 입력</h1>

          <input
            type="password"
            id="password"
            className="form-control"
            placeholder="비밀번호"
            required
            autoFocus
            onChange={onChangePassword}
            style={uncorrectstyle}
          />
          <input
            type="password"
            id="password-check"
            className="form-control"
            placeholder="비밀번호 확인"
            required
            onChange={onChangeConfirmPassword}
            style={uncorrectstyle}
          />
          <div style={commentpassword}>
            비밀번호는 영어,숫자포함 8자에서 20자사이입니다
          </div>

          <button className="btn-login" type="submit">
            비밀번호 찾기
          </button>
          <div className="find"></div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
    </>
  );
}

export default ResetPassword;
