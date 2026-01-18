import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { Separator } from "@app/components/separator/separator";
import { StockTable } from "@app/components/stock-table/stock-table";

@Component({
  selector: 'app-stock-page',
  imports: [ReactiveFormsModule, Separator, StockTable],
  templateUrl: './stock-page.html',
  styleUrl: './stock-page.css',
})
export class StockPage implements OnInit {

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
