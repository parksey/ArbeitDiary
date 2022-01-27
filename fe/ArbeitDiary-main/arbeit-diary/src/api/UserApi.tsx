import axios from "axios";

const MemberInput = {
  userId: "supersfel@naver.com",
  userName: "정민규",
  userPassword: "1",
  userPhone: "1",
};

function UserApi(url: any, callback: any) {
  axios({
    url: "/api" + url,
    method: "post",
    params : MemberInput,

    baseURL: "http://localhost:8080",
    withCredentials: true,
    
  }).then(function (response) {
    callback(response.data);
  });
}

export default UserApi;