import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-gallery-carousel',
  imports: [CommonModule],
  templateUrl: './gallery-carousel.component.html',
  styleUrls: ['./gallery-carousel.component.scss'],
})
export class GalleryCarouselComponent {
  @ViewChild('track', { static: true }) track!: ElementRef<HTMLElement>;
  images: string[] = [
    '/gal/1.webp','/gal/2.webp','/gal/3.webp','/gal/4.webp',
    '/gal/5.jpg','/gal/6.jpg','/gal/7.jpg','/gal/8.webp',
  ];
  index = 0;

  // Desplaza el carril una “tarjeta” a la izquierda (-1) o derecha (+1).
  scroll(direction: number): void {
    const el = this.track?.nativeElement;
    if (!el) return;
    const cardWidth = this.getCardWidth(el);
    el.scrollBy({ left: direction * cardWidth, behavior: 'smooth' });
    // Actualiza índice aproximado
    const approx = Math.round(el.scrollLeft / cardWidth);
    this.index = Math.max(0, Math.min(this.images.length - 1, approx));
  }

  private getCardWidth(el: HTMLElement): number {
    // 296px de ítem + 32px de gap según CSS; calcula realmente desde el DOM si existe
    const first = el.querySelector<HTMLElement>('.gallery__item');
    if (!first) return 328; // fallback
    const style = getComputedStyle(el);
    const gap = parseFloat(style.columnGap || '32') || 32;
    return first.getBoundingClientRect().width + gap;
  }
}

