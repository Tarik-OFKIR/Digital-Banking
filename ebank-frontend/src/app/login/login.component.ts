import { Component, OnInit } from '@angular/core';
import {FormGroup, NgForm} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private Formlogin : FormGroup
  ) {}

  ngOnInit(): void {}

  // login(FormLogin){
  //   const {username , password} = FormLogin.form.value;
  //   this.userAuthService.login(username,password).then((data:any)=>{
  //     console.log("hello")
  //     this.userService.user=data.user;
  //     localStorage.setItem("user",data.user)
  //     localStorage.setItem("token",data.jwtToken)
  //   }).catch(err => console.log(err))


  }
