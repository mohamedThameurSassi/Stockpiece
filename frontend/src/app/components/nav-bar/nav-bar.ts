import { Component, ElementRef, HostListener } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-nav-bar',
  imports: [RouterLink],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
})
export class NavBar {
isMenuOpen = false;

  constructor(private elementRef: ElementRef) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logout() {
    console.log("Déconnexion...");
    this.isMenuOpen = false;
  }

  // Permet de fermer le menu si on clique ailleurs sur l'écran
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.isMenuOpen) return;
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.isMenuOpen = false;
    }
  }
}
