import React, { CSSProperties, useState } from "react";
import { RegistUserApi } from "../api/UserApi";
import "../css/containers/Regist.css";
import CheckEmailModal from "../components/CheckEmailModal";
import Header from "../components/Header";

function Regist() {
  const [password, setPassword] = useState(""); // 비밀번호 일치확인
  const [confirmPassword, setConfirmPassword] = useState("");
  const [visible, setvisible] = useState(false);
  const [correctpassword, setcorrectpassword] = useState(false);
  const passwordType = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;

  const onCancel = () => {
    setvisible(false);
  };

  const onSubmit = (e: any) => {
    e.preventDefault();
    if (passwordType.test(e.target.password.value)) {
      if (password === confirmPassword) {
        setvisible(true);
        RegistUserApi({
          userId: e.target.email.value,
          userPassword: e.target.password.value,
          userName: e.target.name.value,
          userPhone: e.target.phone.value,
        });
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
          <h1 className="h3 mb-3 font-weight-normal">회원가입 정보 입력</h1>

          <input
            type="email"
            id="email"
            className="form-control"
            placeholder="이메일 주소"
            required
            autoFocus
          />
          <input
            type="text"
            id="name"
            className="form-control"
            placeholder="이름"
            required
            autoFocus
          />
          <input
            type="text"
            id="phone"
            className="form-control"
            placeholder="핸드폰 번호"
            required
            autoFocus
          />
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
            회원가입
          </button>
          <div className="find"></div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
      <CheckEmailModal visible={visible} onCancel={onCancel} />
    </>
  );
}

export default Regist;
