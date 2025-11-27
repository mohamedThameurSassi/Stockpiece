import { Component } from '@angular/core';
import { NavBar } from "../../components/nav-bar/nav-bar";
import { Separator } from '@app/components/separator/separator';


@Component({
  selector: 'app-home-page',
  imports: [NavBar, Separator],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {

}
