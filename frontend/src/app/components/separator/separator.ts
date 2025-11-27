import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-separator',
  imports: [],
  templateUrl: './separator.html',
  styleUrl: './separator.css'
})
export class Separator {
  @Input() title: string = 'Separator Component';
  @Input() width: string = '50px';
  @Input() height: string = '600px';
}
