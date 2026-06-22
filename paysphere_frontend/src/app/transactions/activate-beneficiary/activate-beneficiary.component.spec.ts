import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateBeneficiaryComponent } from './activate-beneficiary.component';

describe('ActivateBeneficiaryComponent', () => {
  let component: ActivateBeneficiaryComponent;
  let fixture: ComponentFixture<ActivateBeneficiaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivateBeneficiaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivateBeneficiaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
