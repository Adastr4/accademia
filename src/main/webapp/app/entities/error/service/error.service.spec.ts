import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IError, Error } from '../error.model';

import { ErrorService } from './error.service';

describe('Error Service', () => {
  let service: ErrorService;
  let httpMock: HttpTestingController;
  let elemDefault: IError;
  let expectedResult: IError | IError[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ErrorService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      errorid: 0,
      description: 'AAAAAAA',
      data: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          data: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Error', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          data: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.create(new Error()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Error', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          errorid: 1,
          description: 'BBBBBB',
          data: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Error', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
        },
        new Error()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Error', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          errorid: 1,
          description: 'BBBBBB',
          data: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          data: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Error', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addErrorToCollectionIfMissing', () => {
      it('should add a Error to an empty array', () => {
        const error: IError = { id: 123 };
        expectedResult = service.addErrorToCollectionIfMissing([], error);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(error);
      });

      it('should not add a Error to an array that contains it', () => {
        const error: IError = { id: 123 };
        const errorCollection: IError[] = [
          {
            ...error,
          },
          { id: 456 },
        ];
        expectedResult = service.addErrorToCollectionIfMissing(errorCollection, error);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Error to an array that doesn't contain it", () => {
        const error: IError = { id: 123 };
        const errorCollection: IError[] = [{ id: 456 }];
        expectedResult = service.addErrorToCollectionIfMissing(errorCollection, error);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(error);
      });

      it('should add only unique Error to an array', () => {
        const errorArray: IError[] = [{ id: 123 }, { id: 456 }, { id: 99156 }];
        const errorCollection: IError[] = [{ id: 123 }];
        expectedResult = service.addErrorToCollectionIfMissing(errorCollection, ...errorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const error: IError = { id: 123 };
        const error2: IError = { id: 456 };
        expectedResult = service.addErrorToCollectionIfMissing([], error, error2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(error);
        expect(expectedResult).toContain(error2);
      });

      it('should accept null and undefined values', () => {
        const error: IError = { id: 123 };
        expectedResult = service.addErrorToCollectionIfMissing([], null, error, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(error);
      });

      it('should return initial array if no Error is added', () => {
        const errorCollection: IError[] = [{ id: 123 }];
        expectedResult = service.addErrorToCollectionIfMissing(errorCollection, undefined, null);
        expect(expectedResult).toEqual(errorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
