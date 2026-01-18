import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'tr[app-table-entry]',
  imports: [CommonModule],
  templateUrl: './table-entry.html',
  styleUrl: './table-entry.css',
  host: { class: 'bg-(--background-color2) text-(--text-color) text-center ' }
})
export class TableEntry {

  @Input() stock: any;
}
