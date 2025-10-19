// src/app/features/home/navbar/navbar.component.ts
import { Component, OnInit, OnDestroy, HostListener, ElementRef, inject, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit, AfterViewInit, OnDestroy {
  private elementRef = inject(ElementRef);
  
  isMenuOpen = false;
  isScrolled = false;
  private ticking = false;
  
  ngOnInit() {
    // Detectar scroll inicial
    this.checkScrollPosition();
  }

  ngAfterViewInit() {
    // Forzar reflow para iOS Safari
    this.forceReflow();
    // Verificar y corregir sticky fallback
    this.initStickyFallback();
  }

  ngOnDestroy() {
    // Cleanup si es necesario
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    this.requestTick();
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.forceReflow();
  }

  private requestTick() {
    if (!this.ticking) {
      requestAnimationFrame(() => {
        this.checkScrollPosition();
        this.ticking = false;
      });
      this.ticking = true;
    }
  }

  private checkScrollPosition() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    const newScrolled = scrollPosition > 30; // Reducido para móvil
    
    if (newScrolled !== this.isScrolled) {
      this.isScrolled = newScrolled;
      this.updateNavbarClass();
    }
  }

  private updateNavbarClass() {
    const navbar = this.elementRef.nativeElement.querySelector('.navbar');
    if (navbar) {
      if (this.isScrolled) {
        navbar.classList.add('navbar--scrolled');
      } else {
        navbar.classList.remove('navbar--scrolled');
      }
    }
  }

  private forceReflow() {
    // Fix para iOS Safari sticky issues
    const navbar = this.elementRef.nativeElement.querySelector('.navbar');
    if (navbar) {
      navbar.style.position = 'relative';
      navbar.offsetHeight; // Force reflow
      navbar.style.position = '';
    }
  }

  private initStickyFallback() {
    // Fallback para navegadores que no soportan sticky correctamente
    const navbar = this.elementRef.nativeElement.querySelector('.navbar');
    if (navbar) {
      // Verificar si sticky está funcionando
      const rect = navbar.getBoundingClientRect();
      const isSticky = getComputedStyle(navbar).position === 'sticky';
      
      if (!isSticky) {
        // Implementar fallback con position fixed
        console.warn('Sticky not supported, using fixed fallback');
        this.implementFixedFallback();
      }
    }
  }

  private implementFixedFallback() {
    const navbar = this.elementRef.nativeElement.querySelector('.navbar');
    if (navbar) {
      let isFixed = false;
      const navbarHeight = navbar.offsetHeight;
      
      const handleScroll = () => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        
        if (scrollTop > navbarHeight && !isFixed) {
          navbar.style.position = 'fixed';
          navbar.style.top = '0';
          navbar.style.width = '100%';
          navbar.style.zIndex = '1000';
          isFixed = true;
          document.body.style.paddingTop = navbarHeight + 'px';
        } else if (scrollTop <= navbarHeight && isFixed) {
          navbar.style.position = '';
          navbar.style.top = '';
          navbar.style.width = '';
          navbar.style.zIndex = '';
          isFixed = false;
          document.body.style.paddingTop = '';
        }
      };
      
      window.addEventListener('scroll', handleScroll);
    }
  }

  /**
   * Navega a la raíz del sitio (landing SSR)
   * Sale de la SPA Angular y carga la página Thymeleaf
   */
  navigateToRoot(event: Event): void {
    event.preventDefault();
    window.location.href = '/';
  }

  /**
   * Verifica si estamos en la ruta raíz
   */
  isRootPath(): boolean {
    return window.location.pathname === '/';
  }

  /**
   * Navega a páginas externas (SSR de Thymeleaf)
   */
  navigateExternal(event: Event, path: string): void {
    event.preventDefault();
    window.location.href = path;
  }

  /**
   * Toggle del menú móvil
   */
  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
