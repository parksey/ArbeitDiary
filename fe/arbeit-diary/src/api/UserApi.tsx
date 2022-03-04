import axios from "axios";
import { useHistory } from "react-router";
import { getCalendarinfo } from "../module/Calendar";
import { get_userinfo, Usertype } from "../module/User";

// export const api = "http://localhost:8080";
export const api = "http://54.180.70.202";

type UserApiprops = {
  userId: string;
  userPassword: string;
};

type RegistApiprops = {
  userId: string;
  userPassword: string;
  userName: string;
  userPhone: string;
};

type EmailRequestApiprops = {
  id: string;
};

export async function getUserinfoApi(token: any, dispatch: any) {
  if (token !== null) {
    try {
      await axios({
        method: "get",
        url: `${api}/api/oldproject`,
        headers: {
          'Accept': "application/json", //prettier-ignore
          'Content-Type': "application/json", //prettier-ignore
          Authorization: token,
        },
      }).then((response) => {
        dispatch(get_userinfo(response.data));
      });
    } catch (e) {
      console.log("get getUserinfoApi error!!");
      return false;
    }
  } else {
    return false;
  }
}

export async function UserApi(params: UserApiprops) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/login`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      const { authorization } = response.headers;
      localStorage.setItem("token", authorization);
      console.log(response.data);
    });
  } catch (e) {
    console.log("get UserApi error!!");
  }
}

export async function RegistUserApi(params: RegistApiprops) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/userRegist`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      const { memberRefreshToken } = response.data;
      console.log(memberRefreshToken);
      localStorage.setItem("token", memberRefreshToken);
      console.log(response.data);
    });
  } catch (e) {}
}

export async function findpasswordapi(params: any) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/find/password`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      console.log(response.data);
    });
  } catch (e) {
    console.log("error in findpasswordapi");
  }
}

export async function resetpasswordapi(body: any, id: string) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/reset/password`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: body,
      params: {
        id,
      },
    }).then((response) => {
      console.log(response.data);
    });
  } catch (e) {
    console.log("error in resetpasswordapi");
  }
}

export async function findidapi(body: any) {
  let response: never[] = [];
  try {
    response = await axios({
      method: "post",
      url: `${api}/api/find/userid`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: body,
    }).then((response) => {
      return response.data;
    });
  } catch (e) {
    console.log("error in resetpasswordapi");
  }
  return response;
}

export async function Emailrequestapi(params: EmailRequestApiprops) {
  const history = useHistory();

  try {
    await axios({
      method: "post",
      url: `${api}/api/emailAuth`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      if (response.data) {
        history.push("/");
      } else {
        alert("올바르지 않은 인증입니다.");
      }
    });
  } catch (e) {
    alert("올바르지 않은 인증입니다.");
    history.push("/");
  }
}

export async function PostApi(url: string, token: string, params: any) {
  try {
    await axios({
      method: "post",
      url,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
      data: params,
    }).then((response) => {
      console.log(response.data);
      localStorage.setItem("response", response.data);
      return response.data;
    });
  } catch (e) {
    console.log("error in PostApi");
  }
}

export async function getCalendarApi(
  token: string,
  params: any,
  dispatch: any
) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/load`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
      data: params,
    }).then((response) => {
      // console.log("calendar받아오기");
      // console.log(response.data);
      dispatch(getCalendarinfo(response.data));
    });
  } catch (e) {
    console.log("error in getCalendarApi");
  }
}

export async function sendfixedscheduleapi(token: string, params: any) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/auto`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
      data: params,
    }).then((response) => {
      console.log(response.data);
    });
  } catch (e) {
    console.log("error in sendfixedscheduleapi");
  }
}

export async function calendarupdateapi(token: string, params: any) {
  try {
    await axios({
      method: "post",
      url: `${api}/api/update`,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
      data: params,
    }).then((response) => {
      console.log(response.data);
    });
  } catch (e) {
    console.log("error in calendarupdateapi");
  }
}
