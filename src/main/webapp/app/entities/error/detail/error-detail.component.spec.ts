import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErrorDetailComponent } from './error-detail.component';

describe('Error Management Detail Component', () => {
  let comp: ErrorDetailComponent;
  let fixture: ComponentFixture<ErrorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ErrorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ error: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ErrorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ErrorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load error on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.error).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
