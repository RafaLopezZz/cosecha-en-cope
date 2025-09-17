import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../home/navbar/navbar.component';
import { ArticuloService } from '../../../core/services/articulo.service';
import { ArticuloResponse } from '../../../shared/models/articulo-response';
import { CommonModule, CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-articulo-list',
  imports: [CommonModule, CurrencyPipe, NavbarComponent],
  standalone: true,
  templateUrl: './articulo-list.component.html',
  styleUrls: ['./articulo-list.component.scss'],
})
export class ArticuloListComponent implements OnInit {
  articulos: ArticuloResponse[] = [];

  constructor(private articuloService: ArticuloService) {}

  /**
  ngOnInit(): void {
    this.articuloService.listar().subscribe({
      next: (data) => (this.articulos = data),
      error: (err) => console.error('Error al cargar artículos', err)
    });
  }
  */
  ngOnInit(): void {
    this.articuloService.getArticulos().subscribe({
      next: (data) => {
        console.log('Artículos recibidos:', data);
        this.articulos = data;
      },
      error: (err) => console.error('Error al cargar artículos', err),
    });
  }
}
