import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUserinfoApi } from "../api/UserApi";
import Header from "../components/Header";
import Introduce from "../components/Introduce";
import MkProject from "../components/MkProject";

function Index() {
  const dispatch = useDispatch();

  useEffect(() => {
    const token = localStorage.getItem("token");
    getUserinfoApi(token, dispatch);
  }, []);

  return (
    <>
      <Header />
      <MkProject />
      <Introduce />
    </>
  );
}

export default Index;
