import React from "react";
import { Link } from "react-router-dom";
import "../css/containers/Introduce.css";
function Introduce() {
  return (
    <div className="Introduce">
      <div className="comment">함께 만들어 나가는 ,</div>
      <div className="shadowBox intro">
        <div className="Box first">
          아르바이트생도
          <div className="staff">
            <img
              className="img"
              src="https://img.icons8.com/external-icongeek26-flat-icongeek26/100/000000/external-waiter-food-and-delivery-icongeek26-flat-icongeek26-1.png"
            />
            <img
              className="img"
              src="https://img.icons8.com/external-icongeek26-flat-icongeek26/100/000000/external-waiter-food-and-delivery-icongeek26-flat-icongeek26.png"
            />
          </div>
        </div>
        <div className="Box first">
          관리자도
          <img
            className="img"
            src="https://img.icons8.com/external-filled-outline-icons-maxicons/100/000000/external-boss-office-space-filled-outline-filled-outline-icons-maxicons.png"
          />
        </div>
      </div>

      <div className="comment">가장 직관적인 ,</div>
      <div className="shadowBox intro">
        <div className="Box">
          그날의 기록을 일기처럼!
          <div className="schedule-img img"></div>
        </div>
      </div>

      <div className="comment">가장 편리한 ,</div>
      <div className="shadowBox intro">
        <div className="Box">
          일일히 카카오톡, 수기로 스케줄을 작성하셨나요?
          <div className="fixedschedule img"></div>
        </div>
      </div>

      <div className="comment">오직 매장만을 위한 스케줄러 ,</div>
      <div className="logo-title"></div>
      <Link to="/login" className="btn start-btn">
        로그인하고 시작하기
      </Link>
    </div>
  );
}

export default Introduce;
