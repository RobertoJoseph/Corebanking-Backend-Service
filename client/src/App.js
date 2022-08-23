import * as React from "react";
import Button from "@material-ui/core/Button";
import SaveIcon from "@material-ui/icons/Save";
import { Gridit, TextField } from "@material-ui/core";

import Container from "@material-ui/core/Container";
import { Paper, Grid, Typography } from "@material-ui/core";
import { useState, useEffect } from "react";
import Admin from "./components/Admin";
import { BrowserRouter, Switch, Route, Redirect } from "react-router-dom";
import Auth from "./components/Auth/Auth";
import LoanOfficer from "./components/LoanOfficer";

export default function Navbar() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact component={Auth}></Route>
        <Route path="/admin" exact component={Admin}></Route>
        <Route path="/loanofficer" exact component={LoanOfficer}></Route>

      </Switch>
    </BrowserRouter>
  );
}
