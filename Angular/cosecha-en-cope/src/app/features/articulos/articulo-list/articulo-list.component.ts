import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../home/navbar/navbar.component';
import { ArticuloService } from '../../../core/services/articulo.service';
import { ArticuloResponse } from '../../../shared/models/articulo.models';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-articulo-list',
  imports: [CommonModule, FormsModule, NavbarComponent],
  standalone: true,
  templateUrl: './articulo-list.component.html',
  styleUrls: ['./articulo-list.component.scss'],
})
export class ArticuloListComponent implements OnInit {
  articulos: ArticuloResponse[] = [];
  categoriaSeleccionada: string = '';

  get articulosFiltrados() {
    if (!this.categoriaSeleccionada) return this.articulos;
    return this.articulos.filter(
      (a) => String(a.idCategoria) === this.categoriaSeleccionada
    );
  }

  constructor(
    private articuloService: ArticuloService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idCategoria = params.get('idCategoria');
      if (idCategoria) {
        this.cargarArticulosPorCategoria(Number(idCategoria));
      } else {
        this.cargarTodosLosArticulos();
      }
    });
  }

  cargarTodosLosArticulos(): void {
    this.articuloService.getArticulos().subscribe({
      next: (data) => (this.articulos = data),
      error: (err) => console.error('Error al cargar artículos', err),
    });
  }

  cargarArticulosPorCategoria(idCategoria: number): void {
    this.articuloService.getArticulosPorCategoria(idCategoria).subscribe({
      next: (data) => {
        console.log(
          `Artículos de la categoría ${idCategoria} recibidos:`,
          data
        );
        this.articulos = data;
      },
      error: (err) =>
        console.error(
          `Error al cargar artículos de la categoría ${idCategoria}`,
          err
        ),
    });
  }
}
