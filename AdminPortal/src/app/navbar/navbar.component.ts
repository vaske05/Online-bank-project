import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loggedIn: boolean;

  constructor(private loginService: LoginService, private router: Router) {
    if(localStorage.getItem('PortalAdminHasLoggedIn') == '') {
      this.loggedIn = false;
    } else {
      this.loggedIn = true;
    }
   }

   logout() {
     this.loginService.logout().subscribe(
       res => {
         localStorage.setItem('PortalAdminHasLoggedIn', '');
         this.router.navigate(['/login']);
         location.reload();

       },
       err => console.log(err)
     );
     
   }

   getDisplay() {
     if(!this.loggedIn) {
       return "none"
     } else {
       return "";

     }
   }

  ngOnInit() {
  }

}