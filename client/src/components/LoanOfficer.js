import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";

import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axios from "axios";
import { useHistory } from "react-router-dom";

const initialForm = {
  nationalId: "",
  facilityName: "",
  productType: "",
  amount: "",
  numberOfRepayments: "",
};
function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const theme = createTheme();

export default function LoanOfficer() {
  const [form, setForm] = React.useState(initialForm);
  const history = useHistory();

  const checkUser = async () => {
    try {
      console.log(form.nationalId);
      const response = await axios.post("http://localhost:8080/customer", {
        nationalId: form.nationalId,
      });
      if (response) console.log(response);
    } catch (error) {
      console.log(error);
    }
  };

  React.useEffect(() => {
    if (form) setForm(form);
  }, [form]);

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/tanmeyah/login",
        form
      );
      if (response.data.role === "ADMIN") {
        history.push("/admin");
      }
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Add Loan
          </Typography>
          <Box noValidate>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="nationalId"
                  label="National ID"
                  name="nationalId"
                  autoComplete="nationalId"
                  onChange={(e) =>
                    setForm({ ...form, nationalId: e.target.value })
                  }
                  autoFocus
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                  onClick={checkUser}
                  variant="contained"
                  size="small"
                  color="info"
                  fullWidth
                >
                  Check user
                </Button>
              </Grid>

              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  fullWidth
                  name="facilityName"
                  label="Facilty Name"
                  type="text"
                  id="facility"
                  onChange={(e) =>
                    setForm({ ...form, facilityName: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  fullWidth
                  name="productType"
                  label="Product Type"
                  type="text"
                  id="product"
                  onChange={(e) =>
                    setForm({ ...form, productType: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  fullWidth
                  name="amount"
                  label="Amount"
                  type="number"
                  id="amount"
                  onChange={(e) => setForm({ ...form, amount: e.target.value })}
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  fullWidth
                  name="repayments"
                  label="Number Of Repayments"
                  type="number"
                  id="repayments"
                  onChange={(e) =>
                    setForm({ ...form, numberOfRepayments: e.target.value })
                  }
                />
              </Grid>
            </Grid>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={handleSubmit}
            >
              Add Loan to customer
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}
