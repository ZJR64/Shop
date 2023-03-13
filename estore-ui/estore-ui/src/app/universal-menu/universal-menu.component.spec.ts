import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UniversalMenuComponent } from './universal-menu.component';

describe('UniversalMenuComponent', () => {
  let component: UniversalMenuComponent;
  let fixture: ComponentFixture<UniversalMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UniversalMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UniversalMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
