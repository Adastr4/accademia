import * as dayjs from 'dayjs';
import { IError } from 'app/entities/error/error.model';
import { Fonte } from 'app/entities/enumerations/fonte.model';

export interface ISource {
  id?: number;
  sourceid?: string | null;
  description?: string | null;
  fonte?: Fonte | null;
  data?: dayjs.Dayjs | null;
  errors?: IError[] | null;
}

export class Source implements ISource {
  constructor(
    public id?: number,
    public sourceid?: string | null,
    public description?: string | null,
    public fonte?: Fonte | null,
    public data?: dayjs.Dayjs | null,
    public errors?: IError[] | null
  ) {}
}

export function getSourceIdentifier(source: ISource): number | undefined {
  return source.id;
}
