import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';

@Component({
  selector: 'app-gamble-page',
  imports: [ReactiveFormsModule],
  templateUrl: './gamble-page.html',
  styleUrl: './gamble-page.css',
})
export class GamblePage {

  // 1. Créer un FormControl
  searchControl = new FormControl('');
  searchTerm: string = '';

  ngOnInit() {
    // 2. Écouter les changements
    this.searchControl.valueChanges.pipe(
      debounceTime(300), // Attend 300ms de pause après la frappe
      distinctUntilChanged() // Évite de déclencher si la valeur est identique à la précédente
    ).subscribe(value => {
      this.searchTerm = value || '';
      console.log('Recherche lancée pour :', this.searchTerm);
      // Ici, vous pouvez appeler votre service de recherche
    });
  }
}
