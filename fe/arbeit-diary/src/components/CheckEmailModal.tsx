import React from "react";
import "../css/components/CheckEmailModal.css";

type CheckEmailModalProps = {
  visible: boolean;
  onCancel: () => void;
};

function CheckEmailModal({ visible, onCancel }: CheckEmailModalProps) {
  return (
    <div
      className={"background" + (visible ? " visible" : "")}
      onClick={(event) => {
        onCancel();
      }}
    >
      <div className="emailmodal" onClick={(event) => event.stopPropagation()}>
        {/*이벤트 캡쳐링 방지 */}
        회원가입이 완료되었습니다 <br /> <br />
        이메일을 확인해주세요
      </div>
    </div>
  );
}

export default CheckEmailModal;
