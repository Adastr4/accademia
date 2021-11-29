jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErrorService } from '../service/error.service';

import { ErrorComponent } from './error.component';

describe('Error Management Component', () => {
  let comp: ErrorComponent;
  let fixture: ComponentFixture<ErrorComponent>;
  let service: ErrorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ErrorComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { queryParams: {} } },
        },
      ],
    })
      .overrideTemplate(ErrorComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ErrorComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ErrorService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.errors?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
