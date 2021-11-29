jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ErrorService } from '../service/error.service';
import { IError, Error } from '../error.model';
import { ISource } from 'app/entities/source/source.model';
import { SourceService } from 'app/entities/source/service/source.service';

import { ErrorUpdateComponent } from './error-update.component';

describe('Error Management Update Component', () => {
  let comp: ErrorUpdateComponent;
  let fixture: ComponentFixture<ErrorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let errorService: ErrorService;
  let sourceService: SourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ErrorUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ErrorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ErrorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    errorService = TestBed.inject(ErrorService);
    sourceService = TestBed.inject(SourceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Source query and add missing value', () => {
      const error: IError = { id: 456 };
      const source: ISource = { id: 23684 };
      error.source = source;

      const sourceCollection: ISource[] = [{ id: 53179 }];
      jest.spyOn(sourceService, 'query').mockReturnValue(of(new HttpResponse({ body: sourceCollection })));
      const additionalSources = [source];
      const expectedCollection: ISource[] = [...additionalSources, ...sourceCollection];
      jest.spyOn(sourceService, 'addSourceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ error });
      comp.ngOnInit();

      expect(sourceService.query).toHaveBeenCalled();
      expect(sourceService.addSourceToCollectionIfMissing).toHaveBeenCalledWith(sourceCollection, ...additionalSources);
      expect(comp.sourcesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const error: IError = { id: 456 };
      const source: ISource = { id: 28878 };
      error.source = source;

      activatedRoute.data = of({ error });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(error));
      expect(comp.sourcesSharedCollection).toContain(source);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Error>>();
      const error = { id: 123 };
      jest.spyOn(errorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ error });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: error }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(errorService.update).toHaveBeenCalledWith(error);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Error>>();
      const error = new Error();
      jest.spyOn(errorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ error });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: error }));
      saveSubject.complete();

      // THEN
      expect(errorService.create).toHaveBeenCalledWith(error);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Error>>();
      const error = { id: 123 };
      jest.spyOn(errorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ error });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(errorService.update).toHaveBeenCalledWith(error);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSourceById', () => {
      it('Should return tracked Source primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSourceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
