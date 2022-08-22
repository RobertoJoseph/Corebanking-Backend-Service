import * as React from "react";
import Button from "@material-ui/core/Button";
import SaveIcon from "@material-ui/icons/Save";
import { Gridit, TextField } from "@material-ui/core";

import Container from "@material-ui/core/Container";
import { Paper, Grid, Typography } from "@material-ui/core";
import { useState, useEffect } from "react";

import axios from "axios";
import { useHistory } from "react-router-dom";
const initialForm = {
  email: "",
  password: "",
};
export default function Auth() {
  const [branch, setBranch] = useState({});
  const [role, setRole] = useState("");
  const [form, setForm] = useState(initialForm);
  const history = useHistory();

  const handleClick = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/tanmeyah/login",
        form
      );
      if (response.data.role === "ADMIN") {
        history.push("/admin");
        
      }
    } catch (err) {
      console.log(err.message);
    }
  };
  return (
    <Paper elevation={3}>
      <Typography variant="h5">Sign in</Typography>
      <Grid container spacing={2} justifyContent="center">
        <Grid justifyContent="center" item md={8}>
          <TextField
            label="Email"
            name="email"
            type="text"
            onChange={(e) => setForm({ ...form, email: e.target.value })}
          ></TextField>
        </Grid>
        <Grid item md={8}>
          <TextField
            label="Password"
            name="password"
            type="password"
            onChange={(e) => setForm({ ...form, password: e.target.value })}
          ></TextField>
        </Grid>
        <Grid item>
          <Button variant="contained" color="primary" onClick={handleClick}>
            Submit
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
}
