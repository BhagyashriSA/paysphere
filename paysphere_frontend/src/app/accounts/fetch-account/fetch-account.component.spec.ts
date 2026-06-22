import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchAccountComponent } from './fetch-account.component';

describe('FetchAccountComponent', () => {
  let component: FetchAccountComponent;
  let fixture: ComponentFixture<FetchAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FetchAccountComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FetchAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
