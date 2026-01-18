import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TableEntry } from "../table-entry/table-entry";

@Component({
  selector: 'app-stock-table',
  imports: [CommonModule, TableEntry],
  templateUrl: './stock-table.html',
  styleUrl: './stock-table.css',
})
export class StockTable {

  @Input() Data: any[] = [];
  
  
}
