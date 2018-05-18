/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { TreatmentDetailComponent } from '../../../../../../main/webapp/app/entities/treatment/treatment-detail.component';
import { TreatmentService } from '../../../../../../main/webapp/app/entities/treatment/treatment.service';
import { Treatment } from '../../../../../../main/webapp/app/entities/treatment/treatment.model';

describe('Component Tests', () => {

    describe('Treatment Management Detail Component', () => {
        let comp: TreatmentDetailComponent;
        let fixture: ComponentFixture<TreatmentDetailComponent>;
        let service: TreatmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [TreatmentDetailComponent],
                providers: [
                    TreatmentService
                ]
            })
            .overrideTemplate(TreatmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TreatmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TreatmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Treatment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.treatment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
